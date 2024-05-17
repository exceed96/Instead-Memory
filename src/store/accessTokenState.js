import { folderModalActions } from "./folderModalState";

const getAccessToken = (error, apiHandler) => {
  return async (dispatch) => {
    const errorCode = error.response.status;
    console.log(errorCode);
    if (errorCode === 390) {
      const authorizationHeader = error.response.headers["authorization"];
      if (authorizationHeader) {
        const bearerToken = authorizationHeader.split(" ")[1];
        localStorage.setItem("userToken", bearerToken);
        await apiHandler();
      }
    }
    if (errorCode === 391) {
      dispatch(folderModalActions.setFolderErrorCode(391));
    }
    if (errorCode === 392) {
      window.location.href = "/error";
    }
  };
};

export default getAccessToken;
