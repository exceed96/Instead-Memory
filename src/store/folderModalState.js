import { createSlice } from "@reduxjs/toolkit";

const folderModalStateInitial = {
  folderInfo: { dirName: "", uuid: "" },
  folderList: [],
  getFolderFlag: true,
  folderErrorCode: 0,
  selectFolder: "",
  selectFolderName: "",
  selectFolderList: [],
  isOpen: false,
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
    setGetFolderFlag(state) {
      state.getFolderFlag = !state.getFolderFlag;
    },
    setFolderList(state, action) {
      state.folderList = action.payload;
    },
    setFolderErrorCode(state, action) {
      state.folderErrorCode = action.payload;
    },
    setSelectFolderUuid(state, action) {
      state.selectFolder = action.payload;
    },
    setSelectFolderList(state, action) {
      state.selectFolderList = action.payload;
    },
    setSelectFolderName(state,action){
      state.selectFolderName = action.payload;
    }
  },
});

export const folderModalActions = folderModalState.actions;
export default folderModalState.reducer;
