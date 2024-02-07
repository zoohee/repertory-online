import { useContext } from 'react';
import { useLoaderData } from 'react-router-dom';

import { Source } from '@/types';
import { sourcesContext } from '@/store/sources-context';
import DanceGridBox from './DanceGridBox';
import SourceItem from '@/components/dance/Source';

const SourceList = () => {
  const sources = useLoaderData() as Source[];

  const { selectedTags } = useContext(sourcesContext);

  const selectSource = () => {
    if (selectedTags.length === 0) {
      return sources;
    }
    return sources.filter((source) =>
      source.tagList.some((tag) => selectedTags.includes(tag.tagName))
    );
  };

  return (
    <DanceGridBox column={4}>
      {selectSource().map((source) => (
        <SourceItem key={source.sourceId} source={source} />
      ))}
    </DanceGridBox>
  );
};

export default SourceList;
