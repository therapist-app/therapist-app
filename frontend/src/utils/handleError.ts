import { AxiosError } from 'axios';

export const handleError = (error: AxiosError): string => {
  const response = error.response;

  // catch 4xx and 5xx status codes
  if (response && `${response.status}`.match(/^[4|5]\d{2}$/)) {
    let info = '';

    if (response.data && (response.data as any).detail) {
      info += (response.data as any).detail;
    } else if (response.data && (response.data as any).message) {
      info += (response.data as any).message;
    } else {
      info += `\nstatus code: ${response.status}`;
      info += `\nerror message:\n${JSON.stringify(response.data, null, 2)}`;
    }

    return info;
  } else {
    if (error.message && error.message.match(/Network Error/)) {
      alert('The server cannot be reached.\nDid you start it?');
    }

    return error.message ? error.message : 'An unknown error occurred';
  }
};