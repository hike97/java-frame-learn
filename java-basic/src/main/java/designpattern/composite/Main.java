package designpattern.composite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hike97 2month
 * @create 2021-05-10 19:49
 * @desc 组合设计模式 主要应用于树
 **/
public class Main {
    public static void main (String[] args) {
        //创建树形结构
        BranchNode root = new BranchNode ("root");
        BranchNode chapter01 = new BranchNode ("chapter01");
        BranchNode chapter02 = new BranchNode ("chapter02");
        LeafNode r1 = new LeafNode ("r1");
        LeafNode c11 = new LeafNode ("c11");
        LeafNode c12 = new LeafNode ("c12");
        BranchNode section21 = new BranchNode ("section21");
        LeafNode c211 = new LeafNode ("c211");
        LeafNode c212 = new LeafNode ("c212");

        root.add (chapter01);
        root.add (chapter02);
        root.add (r1);
        chapter01.add (c11);
        chapter01.add (c12);
        chapter02.add (section21);
        section21.add (c211);
        section21.add (c212);

        //便利树
        tree(root,0);

    }

    static void tree(Node b ,int depth){
        for (int i = 0; i < depth; i++) {
            System.out.print ("--");
        }
        b.p ();
        if (b instanceof BranchNode){
            for (Node node : ((BranchNode) b).nodes) {
                tree (node,depth+1);
            }
        }
    }
}
abstract class Node {
    abstract public void p();
}

/**
 * 叶子节点 只有内容 没有下级子节点
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
class LeafNode extends Node {
    String content;
    @Override
    public void p () {
        System.out.println (content);
    }
}

/**
 * 子节点下可添加 节点和叶子节点
 */
@Data
@NoArgsConstructor
class BranchNode extends Node {
    List<Node> nodes = new ArrayList<> ();
    String name;

    public BranchNode (String name) {
        this.name = name;
    }

    @Override
    public void p () {
        System.out.println (name);
    }

    public void add(Node n) {
        nodes.add (n);
    }
}

