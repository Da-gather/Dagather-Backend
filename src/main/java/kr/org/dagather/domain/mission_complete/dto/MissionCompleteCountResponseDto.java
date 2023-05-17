package kr.org.dagather.domain.mission_complete.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MissionCompleteCountResponseDto {
    private Integer total = 0;
    private Integer category0 = 0;
    private Integer category1 = 0;
    private Integer category2 = 0;
    private Integer category3 = 0;
    
    @Builder
    public MissionCompleteCountResponseDto(List<List<Integer>> entity) {
        for (List<Integer> e : entity) {
            total += e.get(1);
            switch (e.get(0)) {
                case 0:
                    category0 = e.get(1);
                    break;
                case 1:
                    category1 = e.get(1);
                    break;
                case 2:
                    category2 = e.get(1);
                    break;
                case 3:
                    category3 = e.get(1);
                    break;
            }
        }
    }
}
