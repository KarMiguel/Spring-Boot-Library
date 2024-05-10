package io.github.karMiguel.library.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileResponseVO {

    @NotBlank(message = "O nome do arquivo não pode estar em branco")
    private String fileName;

    @NotBlank(message = "A URI de download do arquivo não pode estar em branco")
    private String fileDownloadUri;

    @NotBlank(message = "A URI de exclusão do arquivo não pode estar em branco")
    private String fileDeleteUri;

    @NotBlank(message = "O tipo de arquivo não pode estar em branco")
    private String fileType;

    private Long size;

}
