import styled from 'styled-components';
import SourceList from '@/components/project/SourceList';
import ProjectView from '@/components/project/ProjectView';
import Workbench from '@/components/project/Workbench';
import { DndProvider } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';

import React, { useState, useRef, useEffect } from 'react';
import { FFmpeg } from '@ffmpeg/ffmpeg';
import { fetchFile, toBlobURL } from '@ffmpeg/util';
import { formatMilliSecondsToTimeString } from '@/util';
// import { Title } from '@/components/project/Title';
import { ISource } from '@/services/interface';

interface ITrimSection {
  start: number;
  end: number;
}
const Container = styled.div`
  display: flex;
  align-items: center;
`;
const SourceWrapper = styled(Container)`
  z-index: 10;
  margin: var(--sidebar-margin);
  border-radius: 10px;
  max-height: calc(100vh - var(--sidebar-margin) * 2);
  background-color: var(--sidebar-color);
  width: var(--sidebar-project--width);
  justify-content: space-evenly;
  flex-direction: column;
`;
const EditWrapper = styled(Container)`
  z-index: 10;
  margin-right: 10px;
  border-radius: 10px;
  min-height: calc(100vh - var(--sidebar-margin));
  width: calc(100vw - var(--sidebar-margin) * 3);
  min-width: calc(100vw - var(--sidebar-project--width));
  justify-content: space-evenly;
  flex-direction: column;
`;

const ViewWarpper = styled.div`
  border-radius: 10px;
  min-height: calc(64vh - var(--sidebar-margin) * 2);
  background-color: var(--sidebar-color);
  width: 100%;
  min-width: calc(100vw - var(--sidebar-project--width));
  display: flex;
  height: calc(60vh - var(--sidebar-margin) * 2);
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;

const WorkbenchWarpper = styled.div`
  margin-top: 10px;
  border-radius: 10px;
  min-height: calc(36vh - var(--sidebar-margin) * 2);
  background-color: var(--sidebar-color);
  width: calc(100vw - var(--sidebar-project--width));
  width: 100%;
  height: calc(30vh - var(--sidebar-margin) * 2);
  justify-content: space-evenly;
  flex-direction: column;
`;

const ProjectPage: React.FC = () => {
  const [loaded, setLoaded] = useState(false);
  const ffmpegRef = useRef(new FFmpeg());
  const [videoFile, setVideoFile] = useState<File | null>(null);
  const videoRef = useRef<HTMLVideoElement | null>(null);
  const VideoRefList = useRef<HTMLVideoElement[]>([]);
  const messageRef = useRef<HTMLParagraphElement | null>(null);

  if (loaded) console.log('ffmpeg loaded!');
  useEffect(() => {
    load();
  }, []);
  const load = async () => {
    const baseURL = 'https://unpkg.com/@ffmpeg/core@0.12.6/dist/esm';
    const ffmpeg = ffmpegRef.current;
    ffmpeg.on('log', ({ message }) => {
      if (messageRef.current) {
        messageRef.current.innerHTML = message;
      }
    });

    await ffmpeg.load({
      coreURL: await toBlobURL(`${baseURL}/ffmpeg-core.js`, 'text/javascript'),
      wasmURL: await toBlobURL(
        `${baseURL}/ffmpeg-core.wasm`,
        'application/wasm'
      ),
      // use web worker
      workerURL: await toBlobURL(
        `${baseURL}/ffmpeg-core.worker.js`,
        'text/javascript'
      ),
    });
    setLoaded(true);
  };

  // get source (file =>(startMilliseconds, endMilliseconds))
  const trimVideo = async ({ start, end }: ITrimSection) => {
    const ffmpeg = ffmpegRef.current;
    if (!videoFile) {
      console.log('no file');
      return;
    }
    await ffmpeg.writeFile('input.avi', await fetchFile(videoFile));
    console.log(`[FFmpeg] : ${ffmpeg} is written`);
    // copy codec => super fast..
    await ffmpeg.exec([
      '-i',
      'input.avi',
      '-ss',
      formatMilliSecondsToTimeString(start),
      '-to',
      formatMilliSecondsToTimeString(end),
      '-c:v',
      'copy',
      '-c:a',
      'copy',
      '-f',
      'mp4',
      'output.mp4',
    ]);

    const fileData = await ffmpeg.readFile('output.mp4');
    const data = new Uint8Array(fileData as ArrayBuffer);
    if (videoRef.current) {
      videoRef.current.src = URL.createObjectURL(
        new Blob([data.buffer], { type: 'video/mp4' })
      );
      // append to list
      VideoRefList.current.push(videoRef.current);
      console.log(VideoRefList.current);
    }
  };

  // concat video
  const ConcatVideos = async (videos: Array<ISource>) => {
    const ffmpeg = ffmpegRef.current;
    let concatFiles = '';
    console.log(videos.length);
    for (let i = 0; i < videos.length; i++) {
      console.log(videos[i].sourceUrl);
      await ffmpeg.writeFile(
        `concat_input${i}.mp4`,
        await fetchFile(videos[i].sourceUrl)
      );
      await ffmpeg.exec([
        '-i',
        `concat_input${i}.mp4`,
        '-c:v',
        'libx264',
        '-vf',
        'scale=-1:720', // Keep aspect ratio and resize the video to height of 720
        '-c:a',
        'aac',
        '-f',
        'mpegts',
        `transcoded${i}.ts`,
      ]);

      concatFiles += `transcoded${i}.ts|`;
      console.log(videos[i].sourceUrl);
    }
    concatFiles = concatFiles.slice(0, -1);
    console.log(concatFiles);
    await ffmpeg.exec([
      '-i',
      `concat:${concatFiles}`,
      '-c',
      'copy',
      '-bsf:a',
      'aac_adtstoasc',
      'concat_output.mp4',
    ]);

    const fileData = await ffmpeg.readFile('concat_output.mp4');
    const data = new Uint8Array(fileData as ArrayBuffer);
    if (videoRef.current) {
      console.log('videoRef hi');
      videoRef.current.src = URL.createObjectURL(
        new Blob([data.buffer], { type: 'video/mp4' })
      );
      console.log(videoRef.current);
      // append to list
      // VideoRefList.current.push(videoRef.current);
      // console.log(VideoRefList.current);
    } else {
      console.log('bye');
    }
  };
  return (
    <>
      <DndProvider backend={HTML5Backend}>
        <Container>
          <SourceWrapper>
            {/* source list */}
            <SourceList />
          </SourceWrapper>
          <EditWrapper>
            <ViewWarpper>
              {/* video display */}
              <p ref={messageRef}></p>
              <ProjectView
                setVideo={setVideoFile}
                videoRef={videoRef}
                trimVideo={trimVideo}
              />
            </ViewWarpper>
            <WorkbenchWarpper>
              {/* workbench */}
              <Workbench concatVideos={ConcatVideos}></Workbench>
            </WorkbenchWarpper>
          </EditWrapper>
        </Container>
      </DndProvider>
    </>
  );
};

export default ProjectPage;
