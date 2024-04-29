import React from "react";
import AOS from "aos";
import { useLocation } from "react-router-dom";

const SearchResult = (data) => {
  React.useEffect(() => {
    AOS.init({
      duration: 800,
      easing: "ease-in-sine",
      delay: 100,
      offset: 100,
    });
    AOS.refresh();
  }, []);

  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const query = queryParams.get('query');
  console.log(query)

  return (
    <div className="h-screen bg-gray-100 dark:bg-gray-900 pt-20">
    </div>
  );
};

export default SearchResult;
