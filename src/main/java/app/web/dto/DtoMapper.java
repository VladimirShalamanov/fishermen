package app.web.dto;

import app.fisherman.model.Fisherman;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoMapper {

    public static EditProfileRequest fromUser(Fisherman fisherman) {
        return EditProfileRequest.builder()
                .username(fisherman.getUsername())
                .nickname(fisherman.getNickname())
                .password(fisherman.getPassword())
                .imageUrl(fisherman.getImageUrl())
                .build();
    }
}
