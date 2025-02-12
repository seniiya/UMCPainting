package umc.pating.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import umc.pating.config.AwsS3Config;
import umc.pating.entity.common.Uuid;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager{

    private final AmazonS3 amazonS3;

    private final AwsS3Config awsS3Config;

    private final Uuid uuid;

    public String generateReviewKeyName(Uuid uuid) {
        return awsS3Config.getGrimiDrawing() + '/' + uuid.getUuid();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = "daily/" + uuid.randomUuID() + "_" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3.putObject(new PutObjectRequest(awsS3Config.getBucket(), fileName, file.getInputStream(), metadata));

        return amazonS3.getUrl(awsS3Config.getBucket(), fileName).toString();
    }

}