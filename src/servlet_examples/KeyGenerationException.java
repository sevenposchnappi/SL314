package servlet_examples;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

class KeyGenerationException extends Exception {

  public KeyGenerationException() {
    super();
  }
  public KeyGenerationException(String msg) {
    super(msg);
  }
}
