package webapp.resumeanalyzer.application.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import webapp.resumeanalyzer.domain.model.ResumeFile;
import webapp.resumeanalyzer.domain.repository.ResumeFileRepository;
import webapp.resumeanalyzer.domain.service.ResumeFileService;

/**
 * Сервис CRUD методов сущности ResumeFile.
 */
@Service
@Transactional
public class ResumeFileServiceImpl implements ResumeFileService {

    private final ResumeFileRepository resumeFileRepository;

    @Autowired
    public ResumeFileServiceImpl(ResumeFileRepository resumeFileRepository) {
        this.resumeFileRepository = resumeFileRepository;
    }

    public void uploadResumeFile(ResumeFile resumeFile) {
        if (resumeFileRepository.findByOwnerContainingIgnoreCase(resumeFile.getOwner()).size() < 3) {
            resumeFileRepository.save(resumeFile);
        } else {
            deleteResumeFile(resumeFileRepository
                    .findByOwnerContainingIgnoreCase(resumeFile.getOwner())
                    .getFirst());
            resumeFileRepository.save(resumeFile);
        }
    }

    public ResumeFile getResumeFileByFilename(String filename) {
        return resumeFileRepository.findByFilenameContainingIgnoreCase(filename);
    }

    public List<ResumeFile> getResumeFileByOwner(String owner) {
        return resumeFileRepository.findByOwnerContainingIgnoreCase(owner);
    }

    public ResumeFile getResumeFileById(String id) {
        return resumeFileRepository.findById(UUID.fromString(id)).get();
    }

    @Override
    public void deleteResumeFile(ResumeFile resumeFile) {
        resumeFileRepository.delete(resumeFile);
    }

    @Override
    public List<ResumeFile> getAllResumeFiles() {
        return resumeFileRepository.findAll();
    }

    //В дальнейшем заменить owner на пользователя и.т.д
    @Override
    public ResumeFile createResumeFile(MultipartFile file, String ownersName) throws IOException {

        ResumeFile resumeFile = new ResumeFile();
        resumeFile.setFilename(file.getOriginalFilename());
        resumeFile.setFileType(file.getContentType());
        resumeFile.setOwner(ownersName);
        resumeFile.setSize(file.getSize());
        resumeFile.setData(file.getBytes());
        resumeFile.setCreatedAt(LocalDateTime.now());

        return resumeFile;
    }

    @Override
    public File getFile(String id) throws IOException {
        ResumeFile resumeFile = getResumeFileById(id);

        File file = new File(resumeFile.getFilename());
        try(FileOutputStream os = new FileOutputStream(file)) {
            os.write(resumeFile.getData());
        } catch (Exception e) {
            e.getCause();
        }

        return file;

    }

    public String convertCyrilic(ResumeFile resumeFile) {

        String filename = resumeFile.getFilename();

        if (resumeFile.getFileType().equals("application/pdf")) {
            filename = filename.replaceAll(".pdf", "");
        } else {
            filename = filename.replaceAll(".docx", "");
        }

        char[] abcCyr =   {' ','а','б','в','г','д','ѓ','е', 'ж','з','ѕ','и','ј','к','л','љ','м','н','њ','о','п','р','с','т', 'ќ','у', 'ф','х','ц','ч','џ','ш', 'А','Б','В','Г','Д','Ѓ','Е', 'Ж','З','Ѕ','И','Ј','К','Л','Љ','М','Н','Њ','О','П','Р','С','Т', 'Ќ', 'У','Ф', 'Х','Ц','Ч','Џ','Ш','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9','/','-'};
        String[] abcLat = {" ","a","b","v","g","d","]","e","zh","z","y","i","j","c","l","q","m","n","w","o","p","r","s","t","'","u","f","h", "c",";", "x","{","A","B","V","G","D","}","E","Zh","Z","Y","I","J","K","L","Q","M","N","W","O","P","R","S","T","KJ","U","F","H", "C",":", "X","{", "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4","5","6","7","8","9","/","-"};

        if (filename.matches("[A-Za-z+]")) {
            return filename;
        } else {

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < filename.length(); i++) {
                for (int x = 0; x < abcCyr.length; x++) {
                    if (filename.charAt(i) == abcCyr[x]) {
                        builder.append(abcLat[x]);
                    }
                }
            }
            return builder.toString();
        }
    }

    @Override
    public HttpHeaders getHeaders(ResumeFile resumeFile) {
        HttpHeaders headers = new org.springframework.http.HttpHeaders();

        if (resumeFile.getFileType().equals("application/pdf")) {
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=" + convertCyrilic(resumeFile) + ".pdf");
        } else {
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=" + convertCyrilic(resumeFile) + ".docx");
        }
        return headers;
    }
}
