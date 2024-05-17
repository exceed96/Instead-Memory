import { configureStore } from "@reduxjs/toolkit";
import headerReducer from "./headerState";
import mainReducer from "./mainState";
import writeMemoReducers from "./writeMemoState";
import importMemoReducers from "./importMemoState";
import expansionMemoReducers from "./expansionMemoState";
import folderModalReducers from "./folderModalState";
import selectSectionReducers from "./selectSectionState";
import deleteMemoReducers from "./deleteMemoState";
import trashSelectDataReducers from "./trashSelectDataState";

// const headerState = (
//   state = { modal: false, isOpen: false, width: window.innerWidth },
//   action
// ) => {
//   if (action.type === "changeModal") {
//     return {
//       modal: !state.modal,
//       isOpen: !state.isOpen,
//       width: state.width,
//     };
//   }
//   if (action.type === "closeModal") {
//     return {
//       modal: false,
//       isOpen: false,
//     };
//   }
//   if (action.type === "modalWidth") {
//     return {
//       ...state,
//       width: window.innerHeight,
//     };
//   }
//   return state;
// };
const store = configureStore({
  reducer: {
    headerState: headerReducer,
    mainState: mainReducer,
    writeMemoState: writeMemoReducers,
    importMemoState: importMemoReducers,
    expansionMemoState: expansionMemoReducers,
    folderModalState: folderModalReducers,
    selectSectionState: selectSectionReducers,
    deleteMemoState: deleteMemoReducers,
    trashSelectDataState: trashSelectDataReducers,
  },
});

export default store;
