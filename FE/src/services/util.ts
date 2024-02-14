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
