import { useContext } from 'react';

import { sourcesContext } from '@/store/sources-context';
import DanceGridBox from '@/components/dance/DanceGridBox';
import SourceItem from '@/components/dance/sources/SourceItem';

const SourceList = () => {
  const { sources, selectedTags } = useContext(sourcesContext);

  const selectSource = () => {
    if (selectedTags.length === 0) {
      return sources;
    }
    return sources.filter((source) =>
      source.tagNameList.some((tag) => selectedTags.includes(tag))
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
