import sys
sys.path.append('models/research')
sys.path.append('models/research/object_detection')
import os
import glob
import urllib
import utils
import matplotlib.image as mpimg
import tensorflow as tf
from object_detection.utils import visualization_utils as vis_util
import matplotlib.pyplot as plt
import numpy as np

obj_dict = {
    1: {  'id': 1, 'name': 'sol', 'display_name': 'Sol' },
    2: {  'id': 2, 'name': 'indio', 'display_name': 'Indio' },  
    3: {  'id': 3, 'name': 'tecate', 'display_name': 'Tecate Light' },
    4: {  'id': 4, 'name': 'xx', 'display_name': 'XX' },
    5: {  'id': 5, 'name': 'miller', 'display_name': 'Miller' },
    6: {  'id': 6, 'name': 'bohemiaP', 'display_name': 'Bohemia' },
    7: {  'id': 7, 'name': 'strongbowA', 'display_name': 'Strongbow Gold Apple' },
    8: {  'id': 8, 'name': 'strongbowR', 'display_name': 'Strongbow Red Berries' },
    9: {  'id': 9, 'name': 'heineken', 'display_name': 'Heineken' },
    10: {  'id': 10, 'name': 'carta', 'display_name': 'Carta Blanca' },
    11: {  'id': 11, 'name': 'indioC', 'display_name': 'Indio Botella' },
    12: {  'id': 12, 'name': 'tecateC', 'display_name': 'Tecate Caguama' },
    13: {  'id': 13, 'name': 'heineken6', 'display_name': 'Six Heineken' }
}

model_path = 'trained_model/frozen_inference_graph.pb'

def procesaImagen():
    # Load the Tensorflow model into memory.
    detection_graph = tf.Graph()
    with detection_graph.as_default():
        graph_def = tf.GraphDef()
        with tf.gfile.GFile(model_path, 'rb') as fid:
            graph_def.ParseFromString(fid.read())
            tf.import_graph_def(graph_def, name='')
            
        img = mpimg.imread('raw.jpg')
        w, h, _ = img.shape
        img = img.reshape(1, w, h, 3)
        sess = tf.Session(graph=detection_graph)
        boxes, classes, scores, num = sess.run(["detection_boxes:0", "detection_classes:0", "detection_scores:0", "num_detections:0"], {"image_tensor:0": img})
        num = int(num)
        img.setflags(write=1)
        img = np.squeeze(img)
        vis_util.visualize_boxes_and_labels_on_image_array(
              img,
              boxes[0][0:num],
              classes[0][0:num].astype(int),
              scores[0][0:num],
              obj_dict,
              use_normalized_coordinates=True)
        mpimg.imsave('procesada.jpg', img)

