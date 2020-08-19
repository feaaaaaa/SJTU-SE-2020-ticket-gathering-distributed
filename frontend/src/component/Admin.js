import React, { useState, useEffect, useRef } from 'react';
import {Form, Input, InputNumber, Modal, Button, Avatar, Typography, DatePicker, message, Result,Cascader} from 'antd';
import { SmileOutlined, UserOutlined } from '@ant-design/icons';
import "../css/admin.css"
import {addActivity} from "../service/AdminService";
import {Redirect} from "react-router-dom";

const layout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 16 },
};
const tailLayout = {
    wrapperCol: { offset: 8, span: 16 },
};

// interface ModalFormProps {
//     visible: boolean;
//     onCancel: () => void;
// }

// reset form fields when modal is form, closed
const useResetFormOnCloseModal = ({ form, visible }) => {
    const prevVisibleRef = useRef();
    useEffect(() => {
        prevVisibleRef.current = visible;
    }, [visible]);
    const prevVisible = prevVisibleRef.current;
    useEffect(() => {
        if (!visible && prevVisible) {
            form.resetFields();
        }
    }, [visible]);
};

const ModalForm = ({ daycnt,classcnt,visible, onCancel }) => {
    let i;
    const [form] = Form.useForm();

    useResetFormOnCloseModal({
        form,
        visible,
    });

    const onOk = () => {
        form.submit();
    };

    const item = [];
    item.push(
        <Form form={form} layout="vertical" name="userForm">
            <Form.Item name="website"
                       label="website"
                       rules={[{required: true}]}
            >
                <Input/>
            </Form.Item>
        </Form>
    );

    for (i = 0; i < classcnt*daycnt; i++) {
        if(i%classcnt===0){
            item.push(
                <Form form={form} layout="vertical" name="userForm">
                    <Form.Item name={"day" + (Math.floor(i / classcnt) + 1)}
                               label={"day" + (Math.floor(i / classcnt) + 1)}
                               rules={[{required: true}]}
                    >
                        <Input/>
                        {/*<DatePicker style={{ width: '50%' }} />*/}
                    </Form.Item>
                </Form>
            )
        }
        item.push(
            <Form form={form} layout="vertical" name="userForm">
                <Form.Item name={"day"+(Math.floor(i/classcnt)+1)+"class"+(i%classcnt+1)}
                           label={"day"+(Math.floor(i/classcnt)+1)+"class"+(i%classcnt+1)}
                           rules={[{required: true}]}>
                    <InputNumber/>
                </Form.Item>
            </Form>
        );
    }


    return (
        <Modal title="Basic Drawer" visible={visible} onOk={onOk} onCancel={onCancel}>
            {/*<Form form={form} layout="vertical" name="userForm">*/}
            {/*    <Form.Item name="name" label="User Name" rules={[{ required: true }]}>*/}
            {/*        <Input />*/}
            {/*    </Form.Item>*/}
            {/*    <Form.Item name="age" label="User Age" rules={[{ required: true }]}>*/}
            {/*        <InputNumber />*/}
            {/*    </Form.Item>*/}
            {/*</Form>*/}
            {item}
        </Modal>
    );
};
const Demo = () => {
    const [visible, setVisible] = useState(false);

    const[success,setSuccess]=useState(false);

    const[authen,setAuthen]=useState(false);

    const[author,setAuthor]=useState(false);

    const [u, setU] = useState([]);

    const [c, setC] = useState([]);

    const [daycnt, setDaycnt] = useState(0);

    const [classcnt, setClasscnt] = useState(0);

    const showUserModal = () => {
        setVisible(true);
    };

    const hideUserModal = () => {
        setVisible(false);
    };

    const onFinish = values => {
        console.log('Finish:', values);
        console.log('users:',u);
        console.log('category:',c);
        // let activity=[];
        // activity.push(values);
        // activity.push(u);
        var arr = [];
        arr.push(u.length);
        arr.push(daycnt);
        arr.push(classcnt);
        for (let i in values) {
            arr.push(values[i]); //属性
            //arr.push(obj[i]); //值
        }
        for(let i in c){
            arr.push(c[i])
        }
        for(let i in u){
            for(let j in u[i]){
                arr.push(u[i][j])
            }
        }
        // arr=arr.concat(u);
        console.log("activity:"+JSON.stringify(arr));
        addActivity(JSON.stringify(arr),localStorage.getItem("token"),(res)=>{
            // addActivity(0,JSON.stringify(arr),localStorage.getItem("token"),(res)=>{
            //     if(res===true)setSuccess(true);
            // else if(res.message==="authentication failure"){setAuthen(true);localStorage.clear();}
            // else if(res.message==="authorization failure")setAuthor(true);
            // else message.error("错误！请稍后重试");
            // console.log(JSON.stringify(res));
            if(res.status===200&&res.data===true)
                setSuccess(true);
            else if(res.status===-100){
                setAuthen(true);
                localStorage.clear();
            }
            else if(res.status===-101)
                setAuthor(true);
            else message.error(res.msg);
            console.log("add return:"+JSON.stringify(res));
        })
    };

    const options = [
        {
            value: '展览休闲',
            label: '展览休闲',
            children: [
                {
                    value: '展会',
                    label: '展会',
                },
                {
                    value: '特色体验',
                    label: '特色体验',
                },
                {
                    value: '其他展览休闲',
                    label: '其他展览休闲',
                },
            ],
        },
        {
            value: '话剧歌剧',
            label: '话剧歌剧',
            children: [
                {
                    value: '话剧',
                    label: '话剧',
                },
                {
                    value: '音乐剧',
                    label: '音乐剧',
                },
                {
                    value: '其他话剧歌剧',
                    label: '其他话剧歌剧',
                },
            ],
        },
        {
            value: '演唱会',
            label: '演唱会',
            children: [
                {
                    value: '摇滚',
                    label: '摇滚',
                },
                {
                    value: '流行',
                    label: '流行',
                },
                {
                    value: '音乐节',
                    label: '音乐节',
                },
                {
                    value: '其他演唱会',
                    label: '其他演唱会',
                },
            ],
        },
        {
            value: '音乐会',
            label: '音乐会',
            children: [
                {
                    value: '室内乐及古乐',
                    label: '室内乐及古乐',
                },
                {
                    value: '独奏',
                    label: '独奏',
                },
                {
                    value: '管弦乐',
                    label: '管弦乐',
                },
                {
                    value: '其他音乐会',
                    label: '其他音乐会',
                },
            ],
        },
        {
            value: '曲苑杂坛',
            label: '曲苑杂坛',
            children: [
                {
                    value: '戏曲',
                    label: '戏曲',
                },
                {
                    value: '相声',
                    label: '相声',
                },
                {
                    value: '魔术',
                    label: '魔术',
                },
                {
                    value: '其他曲苑杂坛',
                    label: '其他曲苑杂坛',
                },
            ],
        },
        {
            value: '体育',
            label: '体育',
            children: [
                {
                    value: '球类运动',
                    label: '球类运动',
                },
                {
                    value: '田径',
                    label: '田径',
                },
                {
                    value: '篮球',
                    label: '篮球',
                },
                {
                    value: '足球',
                    label: '足球',
                },
                {
                    value: '其他体育',
                    label: '其他体育',
                }
            ],
        },
        {
            value: '舞蹈芭蕾',
            label: '舞蹈芭蕾',
            children: [
                {
                    value: '舞蹈',
                    label: '舞蹈',
                },
                {
                    value: '其他舞蹈芭蕾',
                    label: '其他舞蹈芭蕾',
                }
            ],
        },
        {
            value: '儿童亲子',
            label: '儿童亲子',
            children: [
                {
                    value: '其他儿童亲子',
                    label: '其他儿童亲子',
                }
            ],
        }
    ];

    function onChange(value) {
        console.log(value);
        setC(value);
    }

    const onFinishcnt = values => {
        console.log('Success:', values);
        console.log(classcnt);
        console.log(daycnt);
        setClasscnt(values.classcnt);
        setDaycnt(values.daycnt);
    };

    if (success) return <Result
        status="success"
        title="Successfully Added!"
        extra={[
            <Button type="primary" key="console" href="/sortPage">
                回到搜索页面
            </Button>,
            <Button key="admin" href="/admin">再次添加</Button>,
        ]}
    />;
    else if(authen){
        message.error("请先登录");
        return <Redirect to={{pathname: "/login"}}/>;
    }
    else if(author){
        message.error("无权限");
        return <Redirect to={{pathname: "/login"}}/>;
    }
    else
        return (
            <>
                <Form
                    {...layout}
                    name="cnt"
                    initialValues={{remember: true}}
                    onFinish={onFinishcnt}
                >
                    <Form.Item
                        label="day count"
                        name="daycnt"
                        rules={[{required: true}]}
                    >
                        <InputNumber min={1} max={10}/>
                    </Form.Item>

                    <Form.Item
                        label="class count"
                        name="classcnt"
                        rules={[{required: true}]}
                    >
                        <InputNumber min={1} max={5}/>
                    </Form.Item>

                    <Form.Item {...tailLayout}>
                        <Button type="primary" htmlType="submit">
                            SET CNT
                        </Button>
                    </Form.Item>
                </Form>

                {/*{value1}*/}
                <Form.Provider
                    onFormFinish={(name, {values, forms}) => {
                        if (name === 'userForm') {
                            const {basicForm} = forms;
                            let users = basicForm.getFieldValue('users') || [];
                            // console.log("users:"+JSON.stringify(users));
                            // console.log("value:"+JSON.stringify(values));
                            basicForm.setFieldsValue({users: [...users, values]});
                            users = basicForm.getFieldValue('users') || [];
                            console.log("usersNew:" + JSON.stringify(users));
                            setVisible(false);
                            setU(users);
                        }
                    }}
                >
                    <Form {...layout} name="basicForm" onFinish={onFinish}>

                        <Form.Item name="title" label="title" rules={[{required: true}]}>
                            <Input/>
                        </Form.Item>

                        <Form.Item name="actor" label="actor" rules={[{required: true}]}>
                            <Input/>
                        </Form.Item>

                        <Form.Item name="timescale" label="timescale" rules={[{required: true}]}>
                            <Input/>
                        </Form.Item>

                        <Form.Item name="venue" label="venue" rules={[{required: true}]}>
                            <Input/>
                        </Form.Item>

                        <Form.Item name="imgurl" label="imgurl" rules={[{required: true}]}>
                            <Input/>
                        </Form.Item>

                        <Form.Item name="city" label="city" rules={[{required: true}]}>
                            <Input/>
                        </Form.Item>

                        <div style={{paddingLeft:510}}>
                        <Cascader options={options} onChange={onChange} placeholder="Please select category" />
                        </div>

                        {/*<Form.Item name="category" label="category" rules={[{required: true}]}>*/}
                        {/*    <Input/>*/}
                        {/*</Form.Item>*/}

                        {/*<Form.Item name="subcategory" label="subcategory" rules={[{required: true}]}>*/}
                        {/*    <Input/>*/}
                        {/*</Form.Item>*/}

                        <Form.Item
                            // rules={[{ required: true }]}
                            // name="users"
                            label="Website List"
                            shouldUpdate={(prevValues, curValues) => prevValues.users !== curValues.users}
                            // shouldUpdate={true}
                        >
                            {({getFieldValue}) => {
                                const users = getFieldValue('users') || [];
                                console.log("user:" + JSON.stringify(users));

                                return users.length ? (
                                    <ul>
                                        {users.map((user, index) => (
                                            <li key={index} className="user">
                                                {/*<Avatar icon={<UserOutlined />} />*/}
                                                {user.website}
                                                <Button type="primary" size="small" onClick={()=>{
                                                    u.splice(index,1);
                                                    console.log(u);
                                                    setVisible(false);
                                                }}>delete</Button>
                                                {/*- {user.day}*/}
                                            </li>
                                        ))}
                                    </ul>
                                ) : (
                                    <Typography.Text className="ant-form-text" type="secondary">
                                        ( <SmileOutlined/> No website yet. )
                                    </Typography.Text>
                                );
                            }}
                        </Form.Item>

                        <Form.Item {...tailLayout}>
                            <Button htmlType="submit" type="primary">
                                Submit
                            </Button>
                            <Button htmlType="button" style={{margin: '0 8px'}} onClick={showUserModal}>
                                Add Website
                            </Button>
                        </Form.Item>

                    </Form>

                    <ModalForm visible={visible} onCancel={hideUserModal} daycnt={daycnt} classcnt={classcnt}/>
                </Form.Provider>
            </>
        );
};
export class Admin extends React.Component{
    render() {
         if (localStorage.getItem("usertype") === null) {
             message.error("请先登录");
             return <Redirect to={{pathname: "/login"}}/>;
         } else if (localStorage.getItem("usertype") === "Admin")
            return (
                <div style={{paddingTop:150}}>
                    <Demo/>
                </div>
            );
         else {
             message.error("无权限，请登录");
             return <Redirect to={{pathname: "/login"}}/>;
         }
    }
}
// export class Admin extends React.Component{
//
//     render() {
//         return (
//             <Form name="dynamic_form_item" {...formItemLayoutWithOutLabel} onFinish={onFinish}>
//                 <Form.List name="names">
//                     {(fields, { add, remove }) => {
//                         return (
//                             <div>
//                                 {fields.map((field, index) => (
//                                     <Form.Item
//                                         {...(index === 0 ? formItemLayout : formItemLayoutWithOutLabel)}
//                                         label={index === 0 ? 'Passengers' : ''}
//                                         required={false}
//                                         key={field.key}
//                                     >
//                                         <Form.Item
//                                             {...field}
//                                             validateTrigger={['onChange', 'onBlur']}
//                                             rules={[
//                                                 {
//                                                     required: true,
//                                                     whitespace: true,
//                                                     message: "Please input passenger's name or delete this field.",
//                                                 },
//                                             ]}
//                                             noStyle
//                                         >
//                                             <Input placeholder="passenger name" style={{ width: '60%' }} />
//                                         </Form.Item>
//                                         {fields.length > 1 ? (
//                                             <MinusCircleOutlined
//                                                 className="dynamic-delete-button"
//                                                 style={{ margin: '0 8px' }}
//                                                 onClick={() => {
//                                                     remove(field.name);
//                                                 }}
//                                             />
//                                         ) : null}
//                                     </Form.Item>
//                                 ))}
//                                 <Form.Item>
//                                     <Button
//                                         type="dashed"
//                                         onClick={() => {
//                                             add();
//                                         }}
//                                         style={{ width: '60%' }}
//                                     >
//                                         <PlusOutlined /> Add field
//                                     </Button>
//                                 </Form.Item>
//                             </div>
//                         );
//                     }}
//                 </Form.List>
//
//                 <Form.Item>
//                     <Button type="primary" htmlType="submit">
//                         Submit
//                     </Button>
//                 </Form.Item>
//             </Form>
//         );
//     }
//
// }
