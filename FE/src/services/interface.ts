import { List } from "lodash";

export interface ISource {
  memberId: number;
  sourceCount: number;
  sourceId: number;
  sourceName: string;
  sourceStart: string;
  sourceEnd: string;
  sourceLength: number;
  sourceUrl: string;
  sourceThumbnailUrl: string;
  tagList: List<string>;
}

export interface IRepertoryInfo{
  repertoryName : string;
  sourceList : List<number> // sourceId
  // repertory : Multipart
  // repertoryThumbnail : Multipart

}