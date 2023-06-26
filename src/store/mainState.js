import { createSlice } from "@reduxjs/toolkit";

const mainStateInitial = {
  receiveData: [],
  receiveImportData: [],
  getData: true,
};

const mainState = createSlice({
  name: "mainState",
  initialState: mainStateInitial,
  reducers: {
    receiveData(state, action) {
      state.receiveData = action.payload;
    },
    receiveImportData(state, action) {
      state.receiveImportData = action.payload;
    },
    setGetData(state) {
      state.getData = !state.getData;
    },
  },
});

// export const getMemo = () => {
//   return async (dispatch) => {
//     const getUrl = "https://api.insteadmemo.kr/v1/memo/find";
//     const header = {
//       Authorization: `Bearer ${localStorage.getItem("userToken")}`,
//     };
//     try {
//       if (mainStateInitial.getData) {
//         const data = await axios({
//           method: "get",
//           url: getUrl,
//           headers: header,
//           withCredentials: true,
//         });
//         const sendData = data.data.data.map((el) => (
//           <SavedMemo
//             title={el.title}
//             content={el.content}
//             uuid={el.uuid}
//             important={el.important}
//           />
//         ));
//         dispatch(mainActions.receiveData(sendData));
//         dispatch(mainActions.setGetData());
//       }
//     } catch (error) {
//       const authorizationHeader = error.response.headers["authorization"];
//       if (authorizationHeader) {
//         const bearerToken = authorizationHeader.split(" ")[1];
//         localStorage.setItem("userToken", bearerToken);
//       }
//     }
//   };
// };

export const mainActions = mainState.actions;

export default mainState.reducer;
