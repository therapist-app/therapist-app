import { AxiosError } from "axios";

interface ErrorResponseData {
  detail?: string;
  message?: string;
  [key: string]: unknown;
}

export const handleError = (error: AxiosError): string => {
  const response = error.response;

  // catch 4xx and 5xx status codes
  if (response && `${response.status}`.match(/^[4|5]\d{2}$/)) {
    let info = "";

    const data = response.data as ErrorResponseData;

    if (data.detail) {
      info += data.detail;
    } else if (data.message) {
      info += data.message;
    } else {
      info += `\nstatus code: ${response.status}`;
      info += `\nerror message:\n${JSON.stringify(data, null, 2)}`;
    }

    return info;
  } else {
    if (error.message && error.message.match(/Network Error/)) {
      alert("The server cannot be reached.\nDid you start it?");
    }

    return error.message ? error.message : "An unknown error occurred";
  }
};
