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

export class Tab {
  name: string;
  clicked: boolean;

  constructor(name: string, clicked: boolean) {
    this.name = name;
    this.clicked = clicked;
  }
}

interface Community {
  downloadCount: number;
  feedDate: string;
  feedDisable: boolean;
  feedId: number;
  feedName: string;
  feedThumbnailUrl: string;
  feedType: string;
  feedUrl: string;
  likeCount: number;
  memberId: number;
  memberName: string;
  memberProfile: string;
  originId: number;
}

interface Member {
  memberId: number;
  memberName: string;
  memberProfile: string;
}

export type { Tag, Source, Community, Member };
