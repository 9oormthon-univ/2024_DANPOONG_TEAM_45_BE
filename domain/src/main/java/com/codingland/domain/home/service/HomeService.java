package com.codingland.domain.home.service;

import com.codingland.common.exception.home.HomeErrorCode;
import com.codingland.common.exception.home.HomeException;
import com.codingland.domain.home.dto.ResponseHomeDto;
import com.codingland.domain.home.dto.ResponseHomeListDto;
import com.codingland.domain.home.dto.RequestEditHomeDto;
import com.codingland.domain.home.entity.Home;
import com.codingland.domain.home.repository.HomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final HomeRepository homeRepository;

    /**
     * 홈을 생성합니다.
     */
    public void createHome() {
        Home home = new Home();
        homeRepository.save(home);
    }

    /**
     * 특정 홈을 단 건 조회합니다.
     *
     * @param homeId 조회할 홈의 ID
     * @return 홈 정보 DTO
     * @throws HomeException 홈이 존재하지 않을 경우 예외 발생
     */
    public ResponseHomeDto getHome(Long homeId) {
        Home foundHome = homeRepository.findById(homeId)
                .orElseThrow(() -> new HomeException(HomeErrorCode.NO_HOME_INFO));

        return new ResponseHomeDto(
                foundHome.getId(),
                foundHome.getCharacterId(),
                foundHome.getCharacterType(),
                foundHome.getCharacterName()
        );
    }

    /**
     * 데이터베이스에 등록된 모든 홈을 조회합니다.
     *
     * @return 홈 목록 DTO
     */
    public ResponseHomeListDto getHomeList() {
        List<Home> homes = homeRepository.findAll();
        List<ResponseHomeDto> homeDtoList = homes.stream()
                .map(home -> new ResponseHomeDto(
                        home.getId(),
                        home.getCharacterId(),
                        home.getCharacterType(),
                        home.getCharacterName()
                ))
                .collect(Collectors.toList());
        return new ResponseHomeListDto(homeDtoList);
    }

    /**
     * 홈 정보를 수정합니다.
     *
     * @param homeId 수정할 홈의 ID
     * @return 수정된 홈 엔티티
     * @throws HomeException 홈이 존재하지 않을 경우 예외 발생
     */
    public void editHome(Long homeId,RequestEditHomeDto requestEditHomeDto) {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new HomeException(HomeErrorCode.NO_HOME_INFO));
        ;
        home.editHome(requestEditHomeDto);
        homeRepository.save(home);
    }

    /**
     * 특정 홈을 삭제합니다.
     *
     * @param homeId 삭제할 홈의 ID
     * @throws HomeException 홈이 존재하지 않을 경우 예외 발생
     */
    public void deleteHome(Long homeId) {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new HomeException(HomeErrorCode.NO_HOME_INFO));
        homeRepository.delete(home);
    }
}
