import { createSlice } from "@reduxjs/toolkit";

const expansionMemoStateInitial = {
  updateTitleText: "",
  updateMainText: "",
  updateImportant: false,
  isOpen: false,
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
    updateImportantHandler(state) {
      state.updateImportant = !state.updateImportant;
    },
    setImportantHandler(state, action) {
      state.updateImportant = action.payload;
    },
    setModalHandler(state) {
      state.isOpen = !state.isOpen;
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
