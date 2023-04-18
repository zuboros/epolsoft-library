import { PlusOutlined, LoadingOutlined, UserOutlined } from '@ant-design/icons';
import { Avatar, Upload, message } from 'antd';
import { useState } from 'react';
import { DARK_COLOR } from '../../../common/designConst'
import './uploadAvatar.css'

const getBase64 = (img, setAvatar, callback) => {
   const reader = new FileReader();
   reader.addEventListener('load', () => callback(reader.result));
   reader.readAsDataURL(img);
   setAvatar(img);
};

const beforeUpload = (file) => {
   const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
   if (!isJpgOrPng) {
      message.error('You can only upload JPG/PNG file!');
   }
   const isLt2M = file.size / 1024 / 1024 < 2;
   if (!isLt2M) {
      message.error('Image must smaller than 2MB!');
   }
   return isJpgOrPng && isLt2M;
};

const UploadAvatar = ({ setAvatar }) => {
   const [loading, setLoading] = useState(false);
   const [imageUrl, setImageUrl] = useState();
   const handleChange = (info) => {
      if (info.file.status === 'uploading') {
         setLoading(true);
         return;
      }

      // Get this url from response in real world.
      getBase64(info.file.originFileObj, setAvatar, (url) => {
         setLoading(false);
         setImageUrl(url);
      });
   };
   const uploadButton = (
      <div>
         {loading ? <LoadingOutlined /> : <PlusOutlined />}
         <div
            style={{
               marginTop: 8,
            }}
         >
            Upload
         </div>
      </div>
   );
   return (
      <>
         <Upload
            name="avatar"
            listType="picture-circle"
            showUploadList={false}
            beforeUpload={beforeUpload}
            onChange={handleChange}
            accept=".png,.jpg,.jpeg"
         /* customRequest={(info) => {
            setAvatar([info.file]);
         }} */
         >
            {imageUrl ? (
               <Avatar
                  src={imageUrl}
                  size={228}
                  icon={<UserOutlined />}
                  style={{ backgroundColor: DARK_COLOR }}
               />
            ) : (
               uploadButton
            )}
         </Upload>
      </>
   );
};
export default UploadAvatar;