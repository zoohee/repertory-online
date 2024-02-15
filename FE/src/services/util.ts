export const deriveDaysAgo = (dateString: string) => {
  const givenDate = new Date(dateString);
  const currentDate = new Date();

  const difference = currentDate.getTime() - givenDate.getTime();

  const hoursAgo = Math.floor(difference / (1000 * 60 * 60));
  if (hoursAgo < 24) {
    return `${hoursAgo}시간 전`;
  }

  const daysAgo = Math.floor(hoursAgo / 24);
  return `${daysAgo}일 전`;
};

export const decodeJwt = () => {
  const token = localStorage.getItem('token');
  if (!token) {
    return -1;
  }
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
  return JSON.parse(jsonPayload).memberId;
};
