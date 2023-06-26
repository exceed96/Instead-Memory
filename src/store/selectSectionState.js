import { createSlice } from "@reduxjs/toolkit";

const selectSectionStateInitial = {
  currentSection: {
    note: true,
    important: false,
    folder: false,
  },
};

const selectSectionState = createSlice({
  name: "selectSectionState",
  initialState: selectSectionStateInitial,
  reducers: {
    changeNoteSection(state) {
      state.currentSection = {
        note: true,
        important: false,
        folder: false,
      };
    },
    changeImportantSection(state) {
      state.currentSection = {
        note: false,
        important: true,
        folder: false,
      };
    },
    changeFolderSection(state) {
      state.currentSection = {
        note: false,
        important: false,
        folder: true,
      };
    },
  },
});

export const selectSectionActions = selectSectionState.actions;
export default selectSectionState.reducer;
