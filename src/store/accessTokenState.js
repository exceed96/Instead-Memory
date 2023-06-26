const getAccessToken = (error, apiHandler) => {
  return async (dispatch) => {
    const errorCode = error.response.status;
    if (errorCode === 390) {
      console.log("access token reissuance");
      const authorizationHeader = error.response.headers["authorization"];
      if (authorizationHeader) {
        const bearerToken = authorizationHeader.split(" ")[1];
        localStorage.setItem("userToken", bearerToken);
        if (apiHandler) {
          await apiHandler();
        }
      }
    }
  };
};

export default getAccessToken;
