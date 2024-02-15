import React, { useState, useRef, useEffect } from 'react';
import { FFmpeg } from '@ffmpeg/ffmpeg';
import { fetchFile, toBlobURL } from '@ffmpeg/util';
import { formatMilliSecondsToTimeString } from '@/util';
interface ITrimSection {
  start: number;
  end: number;
}
const Test: React.FC = () => {
  const [loaded, setLoaded] = useState(false);
  const ffmpegRef = useRef(new FFmpeg());
  const [videoFile, setVideoFile] = useState<File | null>(null);
  const videoRef = useRef<HTMLVideoElement | null>(null);
  const VideoRefList = useRef<HTMLVideoElement[]>([]);
  const messageRef = useRef<HTMLParagraphElement | null>(null);

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
    if (videoFile) {
      const ffmpeg = ffmpegRef.current;
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
      }
    }
  };

  const getVideoInfo = async () => {
    if (!videoFile) return;
    const ffmpeg = ffmpegRef.current;
    await ffmpeg.load();

    await ffmpeg.writeFile('input.avi', await fetchFile(videoFile));
    // 메타데이터 추출
    // const metadata = await ffmpeg.exec(['-i', 'input.mp4']);
    // 비디오 길이 정보 찾기
    // const durationString = metadata.match(
    //   /Duration: (\d{2}:\d{2}:\d{2}.\d{2}),/
    // )[1];
    // // 비디오 길이를 밀리초로 변환
    // const durationInMilliseconds = convertToMilliseconds(durationString);

    // console.log('Video length in milliseconds:', durationInMilliseconds);
    console.log('Video format:', videoFile.type);
  };
  const handleFileChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      const files = Array.from(e.target.files);

      // 모든 파일에 대하여
      for (const file of files) {
        setVideoFile(file);

        const videoElement = document.createElement('video');
        videoElement.src = URL.createObjectURL(file);
        VideoRefList.current.push(videoElement);

        // 비디오 정보 출력
        // await getVideoInfo();
      }
    }
  };

  return loaded ? (
    <>
      <button onClick={() => trimVideo({ start: 10000, end: 21000 })}>
        Trim Video
      </button>
      <video ref={videoRef} controls></video>
      <br />
      <input type='file' onChange={handleFileChange} multiple />

      <p ref={messageRef}></p>
    </>
  ) : (
    <button onClick={load}>Load ffmpeg-core (~31 MB)</button>
  );
};

export default Test;
