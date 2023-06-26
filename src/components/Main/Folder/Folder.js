import { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import FolderListItem from "./FolderListItem/FolderListItem";
import getAccessToken from "../../../store/accessTokenState";
import styles from "./Folder.module.css";

const Folder = () => {
  const dispatch = useDispatch();
  const [postFolderName, setPostFolderName] = useState("");
  const [getFoldersNameFlag, setGetFoldersNameFlag] = useState(true);
  const [getFoldersName, setGetFoldersName] = useState([]);

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
    const data = await axios({
      method: "post",
      url: makeFolderPostUrl,
      headers: header,
      withCredentials: true,
      data: finalData,
    });
    setPostFolderName("");
    setGetFoldersNameFlag(true);
  };

  const makeFolderHandler = async (evt) => {
    try {
      evt.preventDefault();
      await makeFolder();
    } catch (error) {
      console.log(error);
      dispatch(getAccessToken(error, makeFolder));
    }
  };

  const getFolders = async () => {
    const folderGetUrl = "https://api.insteadmemo.kr/v2/dir/find";
    const header = {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    };
    if (setGetFoldersNameFlag) {
      const data = await axios({
        method: "get",
        url: folderGetUrl,
        headers: header,
        withCredentials: true,
      });
      const filterGetFolderName = data.data.data.map((el) => (
        <FolderListItem
          key={el.uuid}
          uuid={el.uuid}
          dirName={el.dirName}
          setGetFoldersNameFlag={setGetFoldersNameFlag}
          //   changeEditSection={changeEditSection}
        />
      ));
      setGetFoldersName(filterGetFolderName);
      setGetFoldersNameFlag(false);
    }
  };

  const getFoldersHandler = async () => {
    try {
      await getFolders();
    } catch (error) {
      console.log(error);
      dispatch(getAccessToken(error, getFolders));
    }
  };

  useEffect(() => {
    getFoldersHandler();
  }, [getFoldersNameFlag]);

  return (
    <>
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
          ></input>
          <span className={styles.folderNameInputPlaceHolder}>Folder Name</span>
          <button className={styles.folderNameInputButton}>
            <i class="fa-solid fa-plus"></i>
          </button>
        </section>
      </form>
      <ul className={styles.folderList}>{getFoldersName}</ul>
    </>
  );
};

export default Folder;
