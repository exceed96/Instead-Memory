import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const importMemoStateInitial = {
  modal: false,
  isOpen: false,
  importMemo: [],
  importMemoGetData: true,
};

const importMemoState = createSlice({
  name: "importMemoState",
  initialState: importMemoStateInitial,
  reducers: {
    onOffModal(state) {
      state.modal = !state.modal;
      state.isOpen = !state.isOpen;
    },
    setImportMemo(state, action) {
      state.importMemo = action.payload;
    },
    getImportMemoDataTrueFlag(state) {
      state.importMemoGetData = true;
    },
    getImportMemoDataFalseFlag(state) {
      state.importMemoGetData = false;
    },
  },
});

export const getImportMemo = () => {
  return async (dispatch) => {
    const getUrl =
      "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/v1/memo/find";
    const header = {
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    };
    const data = await axios({
      method: "get",
      url: getUrl,
      headers: header,
    });
    const parseData = data.data.data.filter((memo) => {
      if (memo.important) {
        return memo;
      }
    });
    dispatch(importMemoActions.setImportMemo([...parseData]));
  };
};

export const importMemoActions = importMemoState.actions;

export default importMemoState.reducer;
