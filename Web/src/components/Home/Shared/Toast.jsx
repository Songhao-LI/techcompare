import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";

const Toast = ({ duration, text = () => {} }) => {
  const [showToast, setShowToast] = useState(true);
  const current_user = useSelector(state => state.user.currentUser);

  const hideToast = () => {
    setShowToast(false);
  };

  useEffect(() => {
    // 设置计时器来在2秒后隐藏Toast
    const timer = setTimeout(() => {
      hideToast();
    }, duration);

    // 清除计时器的效果
    return () => clearTimeout(timer);
  }, []); // 空的依赖数组表示这个effect仅在组件首次渲染时执行

  return (
    showToast &&
    <div id="toast" data-aos="fade-left" className="absolute top-10 end-10 -translate-x-1/2 z-50">
      <div
        className="max-w-xs bg-gray-100 border border-gray-200 text-sm text-gray-800 rounded-lg dark:bg-white/10 dark:border-white/20 dark:text-white"
        role="alert">
        <div className="flex p-4">
          Hi, {current_user.username}. Welcome back!
          <div className="ms-auto">
            <button type="button" onClick={hideToast}
                    className="inline-flex flex-shrink-0 justify-center items-center size-5 rounded-lg text-gray-800 opacity-50 hover:opacity-100 focus:outline-none focus:opacity-100 dark:text-white">
              <span className="sr-only">Close</span>
              <svg className="flex-shrink-0 size-4" xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                   viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                   stroke-linejoin="round">
                <path d="M18 6 6 18"></path>
                <path d="m6 6 12 12"></path>
              </svg>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Toast;
