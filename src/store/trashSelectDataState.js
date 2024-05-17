import { createSlice } from "@reduxjs/toolkit";

const trashSelectDataStateInitial = {
  confirmModalFlag: false,
  selectData: [],
};

const trashSelectDataState = createSlice({
  name: "trashSelectDataState",
  initialState: trashSelectDataStateInitial,
  reducers: {
    selectData(state, action) {
      state.selectData = [...action.payload];
    },
    setConfirmModalFlag(state) {
      state.confirmModalFlag = !state.confirmModalFlag;
    },
  },
});

export const trashSelectDataStateAction = trashSelectDataState.actions;

export default trashSelectDataState.reducer;
