package team.luckyturkey.danceservice.controller.requestdto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class PostSourceRequest {
    private String sourceName;
	private int sourceLength;
	private List<String> tagNameList;
	private String start;
	private String end;
}
