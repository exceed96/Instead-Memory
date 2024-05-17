import { createSlice } from "@reduxjs/toolkit";

const deleteMemoStateInitial = {
  receiveDeleteData: [],
};
const deleteMemoState = createSlice({
  name: "deleteMemoState",
  initialState: deleteMemoStateInitial,
  reducers: {
    receiveDeleteData(state, action) {
      state.receiveDeleteData = action.payload;
    },
  },
});

export const deleteMemoActions = deleteMemoState.actions;

export default deleteMemoState.reducer;
