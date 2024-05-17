import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import getAccessToken from "../../../store/accessTokenState";
import styles from "./Folder.module.css";
import { folderModalActions } from "../../../store/folderModalState";
import FolderErrorModal from "./FolderErrorModal/FolderErrorModal";
import SelectFolder from "./SelectFolder/SelectFolder";

const Folder = () => {
  const dispatch = useDispatch();
  const [postFolderName, setPostFolderName] = useState("");

  const folderList = useSelector((state) => state.folderModalState.folderList);

  const folderErrorCode = useSelector(
    (state) => state.folderModalState.folderErrorCode
  );

  const selectFolder = useSelector(
    (state) => state.folderModalState.selectFolder
  );

  const selectFolderName = useSelector(
    (state) => state.folderModalState.selectFolderName
  );

  const changeFolderName = (evt) => {
    setPostFolderName(evt.target.value);
  };
  const makeFolder = async (evt) => {
    const makeFolderPostUrl = "https://api.insteadmemo.kr/v2/dir/save";
    const header = {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    };
    const finalData = JSON.stringify({
      dirName: postFolderName,
    });
    await axios({
      method: "post",
      url: makeFolderPostUrl,
      headers: header,
      withCredentials: true,
      data: finalData,
    });
    setPostFolderName("");
    dispatch(folderModalActions.setGetFolderFlag());
  };

  const makeFolderHandler = async (evt) => {
    try {
      evt.preventDefault();
      await makeFolder();
    } catch (error) {
      dispatch(getAccessToken(error, makeFolder));
    }
  };
  console.log(folderErrorCode);
  return (
    <>
      {folderErrorCode === 391 && <FolderErrorModal />}
      {selectFolder ? (
        <SelectFolder uuid={selectFolder} dirName={selectFolderName} />
      ) : (
        <main>
          <form onSubmit={makeFolderHandler}>
            <section className={styles.folderNameInputSection}>
              <input
                type="text"
                onChange={changeFolderName}
                id="makeFolder"
                className={styles.folderNameInput}
                value={postFolderName}
                required
                maxLength="10"
              />
              <span className={styles.folderNameInputPlaceHolder}>
                Folder Name
              </span>
              <button className={styles.folderNameInputButton}>
                <i className="fa-solid fa-plus"></i>
              </button>
            </section>
          </form>
          <ul className={styles.folderList}>{folderList}</ul>
        </main>
      )}
    </>
  );

  // {
  /* <form onSubmit={makeFolderHandler}>
        <section className={styles.folderNameInputSection}>
          <input
            type="text"
            onChange={changeFolderName}
            id="makeFolder"
            className={styles.folderNameInput}
            value={postFolderName}
            required
            maxLength="10"
          ></input>
          <span className={styles.folderNameInputPlaceHolder}>Folder Name</span>
          <button className={styles.folderNameInputButton}>
            <i class="fa-solid fa-plus"></i>
          </button>
        </section>
      </form>
      <ul className={styles.folderList}>{folderList}</ul> */
  // }
};

export default Folder;
