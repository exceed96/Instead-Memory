import { createSlice } from "@reduxjs/toolkit";

const folderModalStateInitial = {
  folderInfo: { dirName: "", uuid: "" },
};

const folderModalState = createSlice({
  name: "folderModalState",
  initialState: folderModalStateInitial,
  reducers: {
    setSeletcFolder(state, action) {
      state.folderInfo = {
        dirName: action.payload.dirName,
        uuid: action.payload.uuid,
      };
    },
  },
});

export const folderModalActions = folderModalState.actions;
export default folderModalState.reducer;
