import { createSlice } from "@reduxjs/toolkit";

const mainStateInitial = { receiveData: [], getData: true };

const mainState = createSlice({
  name: "mainState",
  initialState: mainStateInitial,
  reducers: {
    receiveData(state, action) {
      state.receiveData = action.payload;
    },
    setGetData(state) {
      state.getData = !state.getData;
    },
  },
});

export const mainActions = mainState.actions;

export default mainState.reducer;
