package com.sungmook.repository;

import com.sungmook.domain.UserOwnSticker;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public interface UserOwnStickerRepository extends JpaRepository<UserOwnSticker, Long> {
}
