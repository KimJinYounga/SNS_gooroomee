package com.gooroomee.api.files.profileImage;

import com.gooroomee.api.files.postfile.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, String> {
    Optional<ProfileImage> findAllByEmail(String email);
}
