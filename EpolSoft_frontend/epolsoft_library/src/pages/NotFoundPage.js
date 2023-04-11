import { Link } from "react-router-dom";
import { Button, Result } from 'antd';
const NotFoundPage = () => {
   return (
      <Result
         status="404"
         title="404"
         subTitle="Sorry, the page you visited does not exist."
         extra={<div>
            <Link to="/">Home</Link>
         </div>}
      />

   )
}

export default NotFoundPage;