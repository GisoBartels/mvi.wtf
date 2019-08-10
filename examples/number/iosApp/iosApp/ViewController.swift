import UIKit
import app

class ViewController: UIViewController, NumberView {
    
    let numberView = NumberViewIos()
    
    override func viewDidLoad() {
        numberView.render(numberText: label)
    }
    
    @IBOutlet weak var label: UILabel!

    @IBAction func minusPressed() {
        numberView.minusPressed()
    }
    
    @IBAction func plusPressed() {
        numberView.plusPressed()
    }
}
