import { useState, useContext } from 'react';
import styled from 'styled-components';

import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

import MoreButton from '@/components/common/More';
import MenuList from '@/components/common/MenuList';
import MenuButton from '@/components/common/MenuButton';
import { deleteSource } from '@/services/dance';
import { sourcesContext } from '@/store/sources-context';

const Hover = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 1;
`;

interface Props {
  id: number;
}

const DanceHover = ({ id }: Props) => {
  const [clicked, setClicked] = useState(false);
  const { removeSource } = useContext(sourcesContext);

  const handleClickButton = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    setClicked((prev) => !prev);
  };

  const requestDelete = () => {
    deleteSource(id).then(() => {
      removeSource(id);
    });
  };

  return (
    <Hover>
      <MoreButton onClick={handleClickButton} />
      {clicked && (
        <MenuList>
          <MenuButton name="수정" onClick={() => {}}>
            <EditIcon fontSize="small" />
          </MenuButton>
          <MenuButton name="삭제" onClick={requestDelete}>
            <DeleteIcon fontSize="small" className="red" />
          </MenuButton>
        </MenuList>
      )}
    </Hover>
  );
};

export default DanceHover;
