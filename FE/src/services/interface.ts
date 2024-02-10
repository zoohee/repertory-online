import { List } from "lodash";

export interface ISource {
  memberId: number;
  sourceCount: number;
  sourceId: number;
  sourceName: string;
  sourceStart: string;
  sourceEnd: string;
  sourceLength: string;
  sourceUrl: string;
  sourceThumbnailUrl: string;
  tagList: List<string>;
}