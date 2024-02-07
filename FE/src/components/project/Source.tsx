import { List } from 'lodash';
import Image from '../common/Image';
import styled from 'styled-components';

interface sourceInterface {
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
interface sourceProps {
  sourceInfo: sourceInterface;
}
const Source = ({ sourceInfo }: sourceProps) => {
  return (
    <>
      <Image size={140} src={sourceInfo.sourceThumbnailUrl}></Image>
      {/* <h1>{sourceInfo.sourceName}</h1> */}
    </>
  );
};

export default Source;
