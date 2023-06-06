import { createSlice } from "@reduxjs/toolkit";

const headerStateInitial = {
  modal: false,
  isOpen: false,
  width: window.innerWidth,
};

const headerState = createSlice({
  name: "headerState",
  initialState: headerStateInitial,
  reducers: {
    changeModal(state) {
      state.modal = !state.modal;
      state.isOpen = !state.isOpen;
      state.width = state.width;
    },
    closeModal(state) {
      state.modal = false;
      state.isOpen = false;
    },
    modalWidth(state) {
      state.width = window.innerHeight;
    },
  },
});

export const headerActions = headerState.actions;

export default headerState.reducer;
