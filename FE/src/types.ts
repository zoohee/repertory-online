interface Tag {
  tagId: number;
  memberId: number;
  tagName: string;
}

interface Source {
  memberId: number;
  sourceCount: number;
  sourceEnd: string;
  sourceId: number;
  sourceLength: number;
  sourceName: string;
  sourceStart: string;
  sourceThumbnailUrl: string;
  sourceUrl: string;
  tagList: Tag[];
}

export type { Tag, Source };
