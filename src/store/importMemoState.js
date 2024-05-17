import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import getAccessToken from "./accessTokenState";

const importMemoStateInitial = {
  modal: false,
  isOpen: false,
  importMemo: [],
  importMemoGetData: false,
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
    try {
      const getUrl = "https://api.insteadmemo.kr/v1/memo/find";
      const header = {
        Authorization: `Bearer ${localStorage.getItem("userToken")}`,
      };
      const data = await axios({
        method: "get",
        url: getUrl,
        headers: header,
        withCredentials: true,
      });
      const parseData = data.data.data.filter((memo) => {
        if (memo.important) {
          return true;
        }
        return false;
      });
      
      dispatch(importMemoActions.setImportMemo([...parseData]));
    } catch (error) {
      dispatch(getAccessToken(error, getImportMemo));
    }
  };
};

export const importMemoActions = importMemoState.actions;

export default importMemoState.reducer;
