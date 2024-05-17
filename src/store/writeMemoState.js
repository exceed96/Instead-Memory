import { createSlice } from "@reduxjs/toolkit";

const writeMemoStateInitial = {
  inputTitleText: "",
  inputMainText: "",
  importMemo: false,
};

const writeMemoState = createSlice({
  name: "writeMemoState",
  initialState: writeMemoStateInitial,
  reducers: {
    changeTitleText(state, action) {
      state.inputTitleText = action.payload;
    },
    changeMainText(state, action) {
      state.inputMainText = action.payload;
    },
    changeImportMemo(state, action) {
      state.importMemo = !state.importMemo;
    },
    setImportMemo(state, action) {
      state.importMemo = action.payload;
    },
  },
});

export const writeMemoActions = writeMemoState.actions;
export default writeMemoState.reducer;
