package io.github.karMiguel.library.vo;


import jakarta.validation.GroupSequence;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileResponseVO {

    private String fileName;
    private String fileDownloadUri;
    private String fileDeleteUri;
    private String fileType;
    private Long size;

}
