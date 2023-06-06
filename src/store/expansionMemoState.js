import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const expansionMemoStateInitial = {
  updateTitleText: "",
  updateMainText: "",
  singleMemo: {},
  expansionMemo: false,
};

const expansionMemoState = createSlice({
  name: "expansionMemoState",
  initialState: expansionMemoStateInitial,
  reducers: {
    updateTitleTextHandler(state, action) {
      state.updateTitleText = action.payload;
    },
    updateMainTextHandler(state, action) {
      state.updateMainText = action.payload;
    },
    setExpansionMemo(state) {
      state.expansionMemo = !state.expansionMemo;
    },
  },
});

// const getUrl =
//   "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/v1/memo/find/userInfo";

// const header = {
//   "Content-Type": "application/json",
//   Authorization: `Bearer ${localStorage.getItem("userToken")}`,
// };

// export const getExpansionData = (uuid) => {
//   return async (dispatch) => {
//     const getMemo = async (evt) => {
//       const data = await axios({
//         method: "get",
//         url: getUrl,
//         params: uuid,
//         headers: header,
//       });
//       dispatch(expansionMemoActions.setSingleMemo(data.data.data[0]));
//     };
//     getMemo();
//   };
// };

export const expansionMemoActions = expansionMemoState.actions;
export default expansionMemoState.reducer;
