package com.codingland.domain.chapter.service;

import com.codingland.common.exception.chapter.ChapterErrorCode;
import com.codingland.common.exception.chapter.ChapterException;
import com.codingland.common.exception.chapter.IsChapterClearedErrorCode;
import com.codingland.common.exception.chapter.IsChapterClearedException;
import com.codingland.common.exception.user.UserErrorCode;
import com.codingland.common.exception.user.UserException;
import com.codingland.domain.chapter.dto.ResponseIsChapterClearedDto;
import com.codingland.domain.chapter.dto.ResponseIsChapterClearedListDto;
import com.codingland.domain.chapter.entity.Chapter;
import com.codingland.domain.chapter.entity.IsChapterCleared;
import com.codingland.domain.chapter.repository.ChapterRepository;
import com.codingland.domain.chapter.repository.IsChapterClearedRepository;
import com.codingland.domain.user.entity.User;
import com.codingland.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IsChapterClearedService {
    private final IsChapterClearedRepository isChapterClearedRepository;
    private final UserRepository userRepository;
    private final ChapterRepository chapterRepository;

    /**
     * 풀이 완료 된 챕터를 완료처리 하는 메서드.
     * @author 김원정
     * @param chapter_id 챕터의 id
     * @param user_id 유저의 id
     * @throws UserException 유저가 존재하지 않을 경우 예외가 발생합니다.
     * @throws ChapterException 챕터가 존재하지 않을 경우 예외가 발생합니다.
     */
    public void clearedChapter(Long chapter_id, Long user_id) {
        User foundUser = userRepository.findById(user_id)
                .orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
        Chapter foundChapter = chapterRepository.findById(chapter_id)
                .orElseThrow(() -> new ChapterException(ChapterErrorCode.NOT_FOUND_CHAPTER_ERROR));
        IsChapterCleared newIsChapterCleared = IsChapterCleared.thisChapterIsCleared(foundChapter, foundUser);
        isChapterClearedRepository.save(newIsChapterCleared);
    }

    /**
     * 챕터 완료 여부를 단 건 조회하는 메서드입니다.
     * @author 김원정
     * @param isChapterCleared_id 챕터 완료 여부의 id
     * @throws IsChapterClearedException 챕터 완료 여부가 존재하지 않을 경우 예외가 발생합니다.
     */
    public ResponseIsChapterClearedDto getIsChapterCleared(Long isChapterCleared_id) {
        IsChapterCleared foundIsChapterCleared = isChapterClearedRepository.findById(isChapterCleared_id)
                .orElseThrow(() -> new IsChapterClearedException(IsChapterClearedErrorCode.NOT_FOUND_IS_CHAPTER_CLEARED_ERROR));
        return ResponseIsChapterClearedDto.builder()
                .id(foundIsChapterCleared.getId())
                .ChapterId(foundIsChapterCleared.getChapter().getId())
                .userId(foundIsChapterCleared.getUser().getUserId())
                .isCleared(foundIsChapterCleared.isCleared())
                .build();
    }

    /**
     * 데이터베이스에 등록된 챕터 완료 여부를 모두 조회하는 메서드입니다.
     * @author 김원정
     */
    public ResponseIsChapterClearedListDto getAllIsChapterClearedList() {
        List<IsChapterCleared> isChapterClearedList = isChapterClearedRepository.findAll();
        List<ResponseIsChapterClearedDto> responseIsChapterClearedListDtoList = new ArrayList<>();
        for (IsChapterCleared isChapterCleared : isChapterClearedList) {
            responseIsChapterClearedListDtoList.add(
                    ResponseIsChapterClearedDto.builder()
                            .id(isChapterCleared.getId())
                            .ChapterId(isChapterCleared.getChapter().getId())
                            .userId(isChapterCleared.getUser().getUserId())
                            .isCleared(isChapterCleared.isCleared())
                            .build()
            );
        }
        return new ResponseIsChapterClearedListDto(responseIsChapterClearedListDtoList);
    }

    /**
     * 챕터 완료 여부를 수정하는 메서드입니다.
     * @author 김원정
     * @param isChapterCleared_id 챕터 완료 여부의 id
     * @param isCleared 완료 여부
     * @throws IsChapterClearedException 챕터 완료 여부가 존재하지 않을 경우 예외가 발생합니다.
     */
    @Transactional
    public void editIsChapterCleared(Long isChapterCleared_id, boolean isCleared) {
        IsChapterCleared foundIsChapterCleared = isChapterClearedRepository.findById(isChapterCleared_id)
                .orElseThrow(() -> new IsChapterClearedException(IsChapterClearedErrorCode.NOT_FOUND_IS_CHAPTER_CLEARED_ERROR));
        foundIsChapterCleared.changeIsCleared(isCleared);
    }
}
