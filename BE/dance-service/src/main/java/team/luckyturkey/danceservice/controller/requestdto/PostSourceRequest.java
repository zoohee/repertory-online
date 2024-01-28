package team.luckyturkey.danceservice.controller.requestdto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostSourceRequest {
    private String sourceName;
	private int sourceLength;
	private List<String> tagList;
	private String start;
	private String end;
}
