export const DecodeJwt = (token: string) => {
  const base64Url = token.split('.')[1];
  const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  const jsonPayload = decodeURIComponent(
    window
      .atob(base64)
      .split('')
      .map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      })
      .join('')
  );
  // const result = JSON.parse(jsonPayload);
  // console.log(result['memberId']); => memberId
  return JSON.parse(jsonPayload);
};
export const formatMilliSecondsToTimeString = (ms: number) => {
  const totalSeconds = Math.floor(ms / 1000);
  const totalMinutes = Math.floor(totalSeconds / 60);
  const totalHours = Math.floor(totalMinutes / 60);
  const milliseconds = ms % 1000;
  const seconds = totalSeconds % 60;
  const minutes = totalMinutes % 60;
  const hours = totalHours;
  const formattedMilliseconds = ('00' + milliseconds).slice(-3);
  const formattedSeconds = ('00' + seconds).slice(-2);
  const formattedMinutes = ('00' + minutes).slice(-2);
  const formattedHours = ('00' + hours).slice(-2);

  return `${formattedHours}:${formattedMinutes}:${formattedSeconds}.${formattedMilliseconds}`;
};
