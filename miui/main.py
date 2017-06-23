import os
import shutil
import xml.etree.ElementTree as ET

res_folder = '../app/src/main/res'


def parse_xml():
    print('>>> 解析 xml 文件...\n')

    tree = ET.parse(os.path.join(res_folder, 'xml/appfilter.xml'))
    root = tree.getroot()

    icon_map = {}
    # Key: PackageName
    # Value: Drawable Filename

    # 获取根节点下所有 item 节点
    for item in root.findall('item'):
        component = item.get('component')
        drawable = item.get('drawable')

        # 确定是 ComponentInfo 再添加
        if component.startswith('ComponentInfo'):
            # 从 component 中提取包名
            package_name = component[component.index('{') + 1:component.index('/')]
            icon_map[package_name] = drawable

    return icon_map


def move_drawable_to_temp(map):
    print('>>> 复制 Drawable 至临时文件夹...')

    if os.path.exists('icons_temp'):
        shutil.rmtree('icons_temp')

    os.mkdir('icons_temp')

    for key, value in map.items():
        src_path = os.path.join(res_folder, 'drawable-nodpi', '%s.png') % value
        out_path = os.path.join('icons_temp', '%s.png') % key

        shutil.copyfile(src_path, out_path)


if __name__ == '__main__':
    print('>>> 开始运行\n')

    icon_map = parse_xml()
    move_drawable_to_temp(icon_map)
